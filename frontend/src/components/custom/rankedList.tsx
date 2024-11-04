import { Word } from "@/types/Word";

type Props = {
    word: Word,
    ranking: number,
    language: string,
};


export const RankedList = (props: Props) => (
    <div className="grid grid-cols-2 justify-center items-center mb-1">
        <div>
            <span className={`font-semibold text-${props.language} text-2xl`}>{props.ranking + 1}</span>&nbsp;
        </div>
        <div>
            <li key={props.word.word} className="text-center text-lg">
                {props.word.word} - {props.word.count}
            </li>
        </div>
    </div>
);