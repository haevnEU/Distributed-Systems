import {useState} from "react";
import {HeaderComponent} from "./components/HeaderComponent/HeaderComponent";

import "./stylesheets/misc/Main.css"
import "./stylesheets/misc/Misc.css"
import "./stylesheets/misc/Size.css"
import "./stylesheets/misc/Flex.css"
import "./stylesheets/misc/Text.css"
import "./stylesheets/color/ColorDefinitions.css"
import {MovingAverageComponent} from "./components/MovingAverageComponent/MovingAverageComponent";
import {TwitterComponent} from "./components/TwitterComponent/TwitterComponent";
import {MainpageComponent} from "./components/MainpageComponent/MainpageComponent";
import {SharpeRatioComponent} from "./components/SharpeRatioComponent/SharpeRatioComponent";
import {InterestDataComponent} from "./components/InterestDataComponent/InterestDataComponent";

export const SERVER_PATH = "https://hrw-vs-backend.herokuapp.com";

function App() {
  const [activePage, setActivePage] = useState("mainpage");

  /**
   * returns the component that the user selected in the menu
   * @return {JSX.Element} selected component
   */
  const selectPage = () => {
    if (activePage === "mainpage") {
      return <MainpageComponent/>
    } else if (activePage === "movingAverage") {
      return <MovingAverageComponent/>
    } else if (activePage === "twitter") {
      return <TwitterComponent/>
    } else if (activePage === "sharpeRatio") {
      return <SharpeRatioComponent/>
    } else if (activePage === "interestData") {
      return <InterestDataComponent/>
    }
  }

  return (
    <div className="App">
        <HeaderComponent menu={{"mainpage": "Mainpage", "movingAverage": "Moving Average", "sharpeRatio": "Sharpe Ratio", "interestData": "APY", "twitter": "Twitter"}}
                         updateActiveContentPage={(page) => setActivePage(page)}/>
      <div className="main">
        {selectPage()}
      </div>

      <div className="footer"/>
    </div>
  );
}

export default App;
