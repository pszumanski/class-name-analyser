import { useEffect, useState } from "react";
import { Link, useLocation } from "react-router-dom";
import { Button } from "../ui/button";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "../ui/select";
import { Footer } from "./footer";
import { capitalize } from "@/utils/stringUtils";

export const Home = () => {
    const location = useLocation()
    const [language, setLanguage] = useState( "java");

    const textClassName = `text-xl mt-4`;
    const languageClassName = `font-bold text-5xl`;

    useEffect(() => {
        if (location.state != null) {
            setLanguage(location.state)
        }
    }, [])

    return <div className="text-center items-center flex flex-col bg-slate-50 text-slate-700 min-h-[100vh]">
        <div className="my-36 text-6xl font-semibold">
            Class Name Analyser<br className="bg-kotlin hover:bg-kotlinSelected bg-java hover:bg-javaSelected"/>
        </div>
        <div className="flex gap-6 mb-24">
            <p className={textClassName}>Let's analyse class names in popular</p>
            <Select value={language} onValueChange={value => setLanguage(value)} >
                <SelectTrigger className="w-[200px] h-[60px] rounded-xl border-none -mt-1 shadow-none">
                    <SelectValue placeholder="language" />
                </SelectTrigger>
                <SelectContent className="w-[200px]">
                    <SelectItem value="java">
                        <p className={`mx-8 text-java ${languageClassName}`}>Java</p>
                    </SelectItem>
                    <SelectItem value="kotlin">
                        <p className={`mx-3 text-kotlin ${languageClassName}`}>Kotlin</p>
                    </SelectItem>
                </SelectContent>
            </Select>
            <p className={textClassName}>projects 👉</p>
            <Link to={`/${language}/analysis`}>
                <Button className={`bg-${language} hover:bg-${language}Selected w-[200px] h-[60px] shadow-xl rounded-xl`}>
                    <p className="font-semibold text-3xl text-center"> Analyse 🔍</p>
                </Button>
            </Link>
        </div>
        <div className="w-[1/2vw] mb-28">
            <p className="text-slate-500">
                Provides analysis of class names in {capitalize(language)}-based popular projects published on GitHub.<br/>
                Due to GitHub API request rate limits, fetches data sequentially over WebSocket connection.<br />
                Checks only classes with UpperCamelCase naming convention.<br />
                Assumes 1 class per file with the same name as filename.<br />
            </p>
        </div>
        <Footer />
    </div>;
}