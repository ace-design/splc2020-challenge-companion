## Global variables

path = '../_datasets/_cleaned'


## Additional setup

if (!requireNamespace("wordspace", quietly=TRUE))
  install.packages("wordspace")
library(wordspace)

#if (!requireNamespace("BiocManager", quietly=TRUE)) {
#  install.packages("BiocManager")
#  BiocManager::install("ComplexHeatmap")
#}

if (!requireNamespace("devtools", quietly=TRUE))
  install.packages("devtools")
library(devtools)

if (!requireNamespace("ComplexHeatmap", quietly=TRUE))
  install_github("jokergoo/ComplexHeatmap")
suppressPackageStartupMessages(library(ComplexHeatmap))

## Required library

library(tidyr)
library(dplyr)
library(ggplot2)

### Merge multiple dataset together

multi_merge <- function(..., key="SCENARIO_ID") {
  Reduce(
    function(x,y) { merge(x,y, by = key) }, 
    list(...))
}


## Load the complete merge dataset

load_dataset <- function(path) {
  raw <- multi_merge(
    read.csv(paste(path, 'merge_git.csv', sep='/')),
    read.csv(paste(path, 'merge_jdime_semistructured.csv', sep='/')),
    read.csv(paste(path, 'merge_jdime_structured.csv', sep='/'))
  )
  ## Keeping only scenarios where we have the results for the three algorithms
  raw <- raw[complete.cases(raw),]
  return(raw)
}

## Load execution time 

load_exec_time <- function(CSVFile) {
  times = read.csv(paste(path, CSVFile, sep='/'))
  # We measure time as user time (computing) + system time (e.g., memory swap)
  tmp <- data.frame(SCENARIO_ID=times$SCENARIO_ID, 
                    TIME = times$USER_TIME)
}


load_all_exec_times <- function() {
  campaign_times_raw <- multi_merge(
    rename(load_exec_time("TIME_merge_git.csv"), GIT=TIME),
    rename(load_exec_time("TIME_merge_git_union.csv"), UNION=TIME),
    rename(load_exec_time("TIME_merge_jdime_structured.csv"), STRUCT_SING=TIME),
    rename(load_exec_time("TIME_merge_jdime_semistructured.csv"), SEMI_SING=TIME),
    rename(load_exec_time("../extras/git_merge_singularity.csv"), GIT_SING=TIME),
    rename(load_exec_time("TIME_corpus_ast.csv"), CORPUS_AST=TIME),
    rename(load_exec_time("TIME_corpus_diffs.csv"), CORPUS_DIFFS=TIME),
    rename(load_exec_time("TIME_corpus_levenshtein.csv"), CORPUS_LEV=TIME),
    rename(load_exec_time("TIME_corpus_locs.csv"), CORPUS_LOCS=TIME),
    rename(load_exec_time("TIME_corpus_parse.csv"), CORPUS_PARSE=TIME),
    rename(load_exec_time("TIME_results_ast.csv"), RESULTS_AST=TIME),
    rename(load_exec_time("TIME_results_diffs.csv"), RESULTS_DIFF=TIME),
    rename(load_exec_time("TIME_results_levensthein.csv"), RESULTS_LEV=TIME),
    rename(load_exec_time("TIME_results_parse.csv"), RESULTS_PARSE=TIME),
    # keep only times for scenarios where we have a result
    data.frame(SCENARIO_ID=load_dataset(path)$SCENARIO_ID) 
  )
  campaign_times_raw <- campaign_times_raw[complete.cases(campaign_times_raw),]
  
  tmp_total <- read.csv(paste(path, "TIME_TOTAL_IN_SECONDS.csv", sep='/'))
  total_time <- data.frame(SCENARIO_ID=tmp_total$SCENARIO_ID, TOTAL=tmp_total$REAL_TIME)
  total_time <- total_time[complete.cases(total_time),]
  
  # Execution time for all the cases where we have merge results
  campaign <- multi_merge(campaign_times_raw, total_time) 
  campaign
}


## Load the result dataset

load_results <- function(path) {
  raw <- read.csv(paste(path, 'results_parse.csv', sep='/'))
  ## Keeping only scenarios where we have the results for the three algorithms
  raw <- raw[complete.cases(raw),]
  return(raw)
}

## Loading the distance dataset

load_distances <- function(path) {
  raw <- multi_merge(
    read.csv(paste(path, 'results_levensthein.csv', sep='/')),
    read.csv(paste(path, 'results_diffs.csv', sep='/')),
    read.csv(paste(path, 'results_ast.csv', sep='/'))
  )
  ## Keeping only scenarios where we have the results for the three algorithms
  raw <- raw[complete.cases(raw),]
  raw$AST_FINAL_TO_GIT_MERGE_NORM <- rowNorms(as.matrix(raw %>% 
                                                          select(contains("AST_FINAL_TO_GIT_MERGE"))))
  raw$AST_FINAL_TO_GIT_MERGE_UNION_NORM <- rowNorms(as.matrix(raw %>% 
                                                                select(contains("AST_FINAL_TO_GIT_MERGE_UNION"))))
  raw$AST_FINAL_TO_JDIME_STRUCT_NORM <- rowNorms(as.matrix(raw %>% 
                                                             select(contains("AST_FINAL_TO_JDIME_STRUCT"))))
  raw$AST_FINAL_TO_JDIME_SEMI_NORM <- rowNorms(as.matrix(raw %>% 
                                                           select(contains("AST_FINAL_TO_JDIME_SEMI"))))
  return(raw)
}



### Ploting distances 

plot_lev_distance <- function(data, title, min=0) {
  obs <- gather(data 
                %>% select(FINAL_TO_GIT, 
                           FINAL_TO_JDIME_STRUCT, 
                           FINAL_TO_JDIME_SEMISTRUCT) 
                %>% rename(
                  F_TO_GIT = FINAL_TO_GIT,
                  F_TO_SEMI = FINAL_TO_JDIME_SEMISTRUCT,
                  F_TO_STRUCT = FINAL_TO_JDIME_STRUCT
                ), key="distance", value="value")
  obs$distance <- as.factor(obs$distance)
  result <- ggplot(obs, aes(x=distance, y=value)) +
    geom_violin(trim=FALSE) + 
    scale_y_log10() +
    labs(title=title, x="File pair", y = "(Levenshtein distance)")
  return(result)
}

plot_diff_distance <- function(data, title, min=0) {
  obs <- gather(data 
                %>% select(DIFF_FINAL_TO_GIT, 
                           DIFF_FINAL_TO_JDIME_STRUCT, 
                           DIFF_FINAL_TO_JDIME_SEMISTRUCT) 
                %>% rename(
                  F_TO_GIT = DIFF_FINAL_TO_GIT,
                  F_TO_SEMI = DIFF_FINAL_TO_JDIME_SEMISTRUCT,
                  F_TO_STRUCT = DIFF_FINAL_TO_JDIME_STRUCT
                ), key="distance", value="value")
  obs$distance <- as.factor(obs$distance)
  result <- ggplot(obs, aes(x=distance, y=value)) +
    geom_violin(trim=FALSE) + 
    scale_y_log10() +
    labs(title=title, x="File pair", y = "(|diff| distance)")
  return(result)
}

plot_ast_distance <- function(data, title, min=0) {
  obs <- gather(data 
                %>% select(ends_with("_NORM")) 
                %>% select(-AST_FINAL_TO_GIT_MERGE_UNION_NORM)
                %>% rename(
                  F_TO_GIT = AST_FINAL_TO_GIT_MERGE_NORM,
                  F_TO_SEMI = AST_FINAL_TO_JDIME_SEMI_NORM,
                  F_TO_STRUCT = AST_FINAL_TO_JDIME_STRUCT_NORM
                ), key="distance", value="value")
  obs$distance <- as.factor(obs$distance)
  result <- ggplot(obs, aes(x=distance, y=value)) +
    geom_violin(trim=FALSE) + 
    scale_y_log10() +
    labs(title=title, x="File pair", y = "(AST distance vector norm)")
  return(result)
}
