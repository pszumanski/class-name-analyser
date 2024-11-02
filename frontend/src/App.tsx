import { Route, Routes } from "react-router-dom";
import { Home } from "./components/custom/home";
import { Analysis } from "./components/custom/analysis";

const App = () => 
    <>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/java/analysis" element={<Analysis language="java"/>} />
        <Route path="/kotlin/analysis" element={<Analysis language="kotlin"/>} />
      </Routes>
    </>

export default App
