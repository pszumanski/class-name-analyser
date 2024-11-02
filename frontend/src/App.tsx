import { useState } from "react";
import { ApiService } from "./api/ApiService";
import { Button } from "./components/ui/button";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "./components/ui/select";
import { Footer } from "./components/custom/footer";

function App() {

  const apiService = new ApiService();

  const [language, setLanguage] = useState("Java")

  const onInit = () => {
    apiService.init(apiService, language)
    console.log('redirect')
  }

  const textClassName = `text-xl mt-4`
  const languageClassName = `font-bold text-5xl`

  return (
    <div className="text-center items-center flex flex-col bg-slate-50 text-slate-700 h-[100vh]">
      <div className="my-36 text-6xl font-semibold">
        Class Name Analyser
      </div>
      <div className="flex gap-6 mb-24">
        <p className={textClassName}>Let's analyse class names in popular</p>
        <Select value={language} onValueChange={value => setLanguage(value)} >
          <SelectTrigger className="w-[200px] h-[60px] rounded-xl border-none -mt-1 shadow-none">
            <SelectValue placeholder="Language" />
          </SelectTrigger>
          <SelectContent className="w-[200px]">
            <SelectItem value="Java">
              <p className={`text-orange-400 mx-8 ${languageClassName}`}>Java</p>
            </SelectItem>
            <SelectItem value="Kotlin">
              <p className={`text-kotlin mx-3 ${languageClassName}`}>Kotlin</p>
            </SelectItem>
          </SelectContent>
        </Select>
        <p className={textClassName}>projects 👉</p>
        <Button onClick={onInit} className={`${language === 'Java' ? 'bg-java hover:bg-javaSelected' : 'bg-kotlin hover:bg-kotlinSelected'} w-[200px] h-[60px] shadow-xl rounded-xl`}>
          <p className="font-semibold text-3xl text-center"> Analyse 🔍</p>
        </Button>
      </div>
      <div className="w-1/2">
        TODO: Add description
        Lorem ipsum dolor sit amet consectetur, adipisicing elit. Voluptatum delectus veritatis porro temporibus, nam et beatae, atque sit unde repudiandae mollitia ab, ad quisquam! Maxime recusandae eos qui pariatur placeat?
      </div>
      <Footer />
    </div>
  )
}

export default App
