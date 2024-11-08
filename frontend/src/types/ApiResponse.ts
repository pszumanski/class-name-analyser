import { Word } from "./Word";

export type ApiResponse = {
    language: string;
    totalRepositoriesCount: number;
    repositoriesAnalysed: number;
    classesAnalysed: number;
    validClasses: number;
    wordsAnalysed: number;
    averageWordsPerClass: number;
    percentageOfValidClasses: number;
    words: Word[];
};