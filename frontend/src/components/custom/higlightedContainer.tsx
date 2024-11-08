type Props = {
    stat: string,
    description: string,
    language: string,
}

export const HiglightedContainer = (props: Props) => (
    <div className="bg-slate-100 border-slate-200 border-2 border-solid  rounded-2xl py-4 shadow-lg transition-transform duration-500 ease-in-out transform hover:scale-105">
        <p className={`font-semibold text-${props.language} text-3xl`}>{props.stat}</p>
        <p className="text-slate-600">{props.description}</p>
    </div>
)