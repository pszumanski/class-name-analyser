import { Link } from "react-router-dom";
import { Button } from "../ui/button";
import { Footer } from "./footer";

export const ErrorPage = () => (
    <div className="bg-slate-50 flex flex-col items-center min-h-[100vh] h-full">
        <div className="text-2xl text-slate-700 text-center flex flex-col gap-5 mt-[25vh] mb-[30vh]">
            <p className="text-5xl">Oh no!</p>
            <div>
                <p>Looks like there was a problem when connecting to backend</p>
                <p>Please, make sure app runs properly and try again</p>
            </div>
            <Link to="/">
                <Button className="bg-slate-600 w-[200px] h-[60px] text-2xl rounded-xl shadow-xl">
                    Home
                </Button>
            </Link>
        </div>
        <Footer />
    </div>
);