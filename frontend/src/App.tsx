import { Route, Routes } from "react-router-dom";
import { Home } from "./components/custom/home";
import { Analysis } from "./components/custom/analysis";
import { ErrorPage } from "./components/custom/error";

const App = () =>
  <>
    <Routes>
      <Route path="/" element={<Home />} />
      <Route path="/error" element={<ErrorPage /> } />
      <Route path="/java/analysis" element={<Analysis language="java" />} />
      <Route path="/kotlin/analysis" element={<Analysis language="kotlin" />} />
    </Routes>
  </>;

export default App;
