import { ApiResponse } from "@/types/ApiResponse";
import { capitalize } from "@/utils/stringUtils";
import { useEffect, useRef, useState } from "react";
import { useNavigate } from "react-router-dom";
import SockJS from "sockjs-client/dist/sockjs";
import * as Stomp from 'stompjs';
import { Button } from "../ui/button";
import { Footer } from "./footer";
import { LimitInfo } from "@/types/LimitInfo";
import { Word } from "@/types/Word";
import { RankedList } from "./rankedList";
import { HiglightedContainer } from "./higlightedContainer";
import { getLimitInfo } from "@/api/limitApi";
import { Switch } from "../ui/switch";

const backendUrl = import.meta.env.VITE_BACKEND_URL;

type Props = {
    language: string;
};

export const Analysis = (props: Props) => {
    const navigate = useNavigate();
    const language: string = props.language;
    const otherLanguage: string = language == 'java' ? 'kotlin' : 'java';

    const stompClientRef = useRef<Stomp.Client | null>(null);
    const sessionIdRef = useRef<string>(crypto.randomUUID());
    const loadData = useRef<boolean>(true);

    const highlight = `font-semibold text-${language}`;

    const [data, setData] = useState({
        language: "java",
        totalRepositoriesCount: 0,
        repositoriesAnalysed: 0,
        classesAnalysed: 0,
        validClasses: 0,
        wordsAnalysed: 0,
        averageWordsPerClass: 0.0,
        percentageOfValidClasses: 0.0,
        words: [] as Word[]
    } as ApiResponse);

    const [initalized, setInitialized] = useState(false);
    const [waitingTime, setWaitingTime] = useState(-1);
    const [sortedWords, setSortedWords] = useState([] as Word[]);

    const [dots, setDots] = useState('');
    const [limitInfo, setLimitInfo] = useState({
        'remaining': 0,
        'reset': -1
    } as LimitInfo);

    const init = (): Stomp.Client => {
        const stompClient: Stomp.Client = Stomp.over(new SockJS(`${backendUrl}/websocket`));

        stompClient.connect({}, (_) => {
            stompClient.subscribe(`/topic/${sessionIdRef.current}/update`, (message) => {
                setData(JSON.parse(message.body) as ApiResponse);
                setInitialized(true);
                getLimitInfo().then(data => setLimitInfo(data));
            });
        });

        return stompClient;
    };

    const requestData = () => {
        if (stompClientRef.current && loadData.current) {
            stompClientRef.current.send('/app/init', {}, JSON.stringify(
                {
                    language: language,
                    sessionId: sessionIdRef.current
                }
            ));
        }
    };

    const redirectHome = () => {
        navigate('/', { state: `${language == 'java' ? 'kotlin' : 'java'}` });
    };

    useEffect(() => {
        if (!stompClientRef.current) {
            stompClientRef.current = init();
        }

        getLimitInfo().then(data => setLimitInfo(data));
        const interval = setInterval(() => (setDots(prevDots => (prevDots.length < 3 ? prevDots + "." : ""))), 600);

        loadData.current = true;

        return () => (clearInterval(interval));
    }, []);

    useEffect(() => {
        setSortedWords(data.words.sort((a, b) => b.count - a.count));
    }, [data]);

    useEffect(() => {
        if (limitInfo.remaining > 8) {
            setWaitingTime(0);

            if (loadData.current) requestData();
        } else {
            setWaitingTime(Math.ceil(limitInfo.reset - (Date.now() / 1000)));

            if (waitingTime > 0 && loadData.current) {
                setTimeout(() => {requestData()}, waitingTime * 1000);
            }
        }

        const interval = setInterval(() => setWaitingTime(prev => prev - 1), 1000);
        return () => clearInterval(interval);
    }, [limitInfo, loadData.current]);

    return <div className="text-center items-center flex flex-col bg-slate-50 text-slate-600 min-h-[100vh]">
        <div className="absolute text-sm mt-4 -mr-[90vw]">
            <Button onClick={redirectHome} className={`bg-${otherLanguage} hover:bg-${otherLanguage}Selected w-[100] h-[30] opacity-80 shadow-md rounded-xl`}>
                Check {capitalize(otherLanguage)}
            </Button>
        </div>
        {initalized ?
            <div className="text-center">
                <div className="mt-6 mb-[5vh]">
                    <div className="mb-2 text-sm text-slate-500">
                        {waitingTime > 0
                            ? <p>More data available in {waitingTime}s</p>
                            : <p>{loadData.current ? `Loading more data${dots}` : 'More data available'}</p>
                        }
                    </div>
                    <div className="flex flex-row items-center justify-center">
                        <Switch checked={loadData.current} onClick={() => {
                            loadData.current = !loadData.current;
                            getLimitInfo().then(data => setLimitInfo(data));
                        }} />
                        <p className={`text-sm ${loadData && 'font-semibold'} text-slate-500`}>&nbsp;Load</p>
                    </div>
                    
                </div>
                <div className="text-5xl font-semibold">
                    <p className={`inline text-${language}`}>{capitalize(language)}</p> class names analysis
                </div>
                <div className="mt-4 text-slate-400">
                    Did you know? There are <span className="font-semibold">{data.totalRepositoriesCount.toLocaleString()}</span> public {capitalize(language)} projects on GitHub
                </div>
                <div className="grid grid-cols-2 justify-center items-center gap-16 mt-8 mb-8 w-[40vw]">
                    <HiglightedContainer stat={data.repositoriesAnalysed.toFixed(0)} description='popular repositories analysed' language={language} />
                    <HiglightedContainer stat={data.classesAnalysed.toFixed(0)} description='classes analysed' language={language} />
                    <HiglightedContainer stat={data.validClasses.toFixed(0)} description='valid classes' language={language} />
                    <HiglightedContainer stat={`${data.percentageOfValidClasses.toFixed(1)}%`} description='% of valid classes' language={language} />
                </div>
                <div className="my-2">
                    Each class has on average <span className={highlight}>{data.averageWordsPerClass.toFixed(2)}</span> words
                </div>
                <div className="my-2">
                    The most common word is <span className={highlight}>{sortedWords[0]?.word}</span> with {sortedWords[0]?.count} occurrences
                </div>
                <div className="mt-16 mb-32">
                    <div className="font-semibold text-xl mb-4">
                        Top 20 most used words
                    </div>
                    <ol>
                        <div className="grid grid-cols-2 justify-center items-center">
                            <div className="mr-auto">
                                {sortedWords
                                    .slice(0, 10)
                                    .map((word, index) => <RankedList word={word} ranking={index} language={language} />)}
                            </div>
                            <div className="mr-auto">
                                {sortedWords
                                    .slice(10, 20)
                                    .map((word, index) => <RankedList word={word} ranking={index + 10} language={language} />)}
                            </div>
                        </div>
                    </ol>
                </div>
            </div>
            :
            <div className="mt-[35vh] my-[30vh]" onLoad={getLimitInfo}>
                {/* TODO: Spinner out of MVP scope <br /> */}
                Waiting for GitHub Api{dots}<br/>
                {waitingTime > 0
                    ? <p> Please wait {waitingTime.toFixed(0)}s</p>
                    : <p> Loading data{dots} </p>
                }
            </div>
        }
        <Footer />
    </div>;
};