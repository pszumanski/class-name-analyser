import { useState } from "react";
import { Link, useLocation } from "react-router-dom";
import { Button } from "../ui/button";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "../ui/select";
import { Footer } from "./footer";

export const Home = () => {
    const location = useLocation()
    const [language, setLanguage] = useState(location.state != null ? location.state : "java");

    const textClassName = `text-xl mt-4`;
    const languageClassName = `font-bold text-5xl`;

    return <div className="text-center items-center flex flex-col bg-slate-50 text-slate-700 h-[100vh]">
        <div className="my-36 text-6xl font-semibold">
            Class Name Analyser
        </div>
        <div className="flex gap-6 mb-24">
            <p className={textClassName}>Let's analyse class names in popular</p>
            <Select value={language} onValueChange={value => setLanguage(value)} >
                <SelectTrigger className="w-[200px] h-[60px] rounded-xl border-none -mt-1 shadow-none">
                    <SelectValue placeholder="language" />
                </SelectTrigger>
                <SelectContent className="w-[200px]">
                    <SelectItem value="java">
                        <p className={`text-orange-400 mx-8 ${languageClassName}`}>Java</p>
                    </SelectItem>
                    <SelectItem value="kotlin">
                        <p className={`text-kotlin mx-3 ${languageClassName}`}>Kotlin</p>
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
        <div className="w-1/2">
            TODO: Add description
            Lorem ipsum dolor sit amet consectetur, adipisicing elit. Voluptatum delectus veritatis porro temporibus, nam et beatae, atque sit unde repudiandae mollitia ab, ad quisquam! Maxime recusandae eos qui pariatur placeat?
        </div>
        <Footer />
    </div>;
}