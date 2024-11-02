import { ApiResponse } from "@/types/ApiResponse";
import { useEffect, useState } from "react";
import { NavLink, useLocation, useNavigate } from "react-router-dom";
import { Footer } from "./footer";
import SockJS from "sockjs-client/dist/sockjs";
import * as Stomp from 'stompjs';
import { Button } from "../ui/button";

const backendUrl = import.meta.env.VITE_BACKEND_URL;

type Props = {
    language: string
}

export const Analysis = (props: Props) => {
    let isInitialized: boolean = false
    const navigate = useNavigate()
    const language: string = props.language
    const otherLanguage: string = language == 'java' ? 'kotlin' : 'java'
    const sessionId: string = crypto.randomUUID()
    const stompClient: Stomp.Client = Stomp.over(new SockJS(`${backendUrl}/websocket`))
    
    const [data, setData] = useState({
        language: "java",
        totalRepositoriesCount: 0,
        repositoriesAnalysed: 0,
        classesAnalysed: 0,
        validClasses: 0,
        wordsAnalysed: 0,
        averageWordsPerClass: 0.0,
        percentageOfValidClasses: 0.0,
        words: new Map<string, number>()
    } as ApiResponse)

    useEffect(() => {
        init()
    }, [])

    const init = () => {
        stompClient.connect({}, (_) => {
            subscribe();
            requestData();
        });
    }

    const subscribe = () => {
        stompClient.subscribe(`/topic/${sessionId}/update`, (message) => {
            setData(JSON.parse(message.body) as ApiResponse);
            isInitialized = true;
        });
    }

    const requestData = () => {
        stompClient.send('/app/init', {}, JSON.stringify(
            {
                language: language,
                sessionId: sessionId
            }
        ));
    }

    const redirectHome = () => {
        navigate('/', {state: `${language == 'Java' ? 'kotlin' : 'java'}`})
    }

    const capitalize = (language: string): string => `${language.charAt(0).toUpperCase()}${language.substring(1)}`

    return <div className="text-center items-center flex flex-col bg-slate-50 text-slate-700 h-[100vh]">
        <div className="absolute text-sm mt-4 -mr-[90vw]">
            <Button onClick={redirectHome} className={`bg-${otherLanguage} hover:bg-${otherLanguage}Selected w-[100] h-[30] opacity-80 shadow-md rounded-xl`}>
                Check {capitalize(otherLanguage)}
            </Button>
        </div>
        {!isInitialized &&
            <div className="mt-[35vh]">
                TODO: Spinner
            </div>
        }
        {isInitialized &&
            <div className="mt-16">
                {data.averageWordsPerClass}
            </div>
        }
        <Footer />
    </div>
}