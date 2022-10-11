import {LoadingIconComponent} from "../LoadingIconComponent/LoadingIconComponent";
import {useEffect, useState} from "react";
import {SERVER_PATH} from "../../App";
import {MathComponent} from "mathjax-react";

/**
 * Represents the sharpeRatio component, that displays the calculated sharpe ratio and the calculation basis
 * @return {JSX.Element}
 * @constructor
 */
export const SharpeRatioComponent = () => {
    const [content, setContent] = useState(null);

    useEffect(async () => {
        setContent(<LoadingIconComponent/>)

        try {
            // calling the api
            const response = await fetch(SERVER_PATH + '/getSharpeRatio');
            const body = await response.json();

            setContent("Sharpe Ratio: " + body)
        } catch (e) {
            setContent("Error loading the sharpe ratio data")
        }
    }, [])

    return (
        <>
            <h1>Sharpe Ratio</h1> <br/>
            {content}

            <hr/>

            <div className="">
                <h3>Calculation basis</h3>
                <MathComponent tex={String.raw`Sharpe Ratio = {Rp + Rf \over σp}`}/>
                where <br/>
                Rp = return of portfolio <br/>
                Rf = risk-free rate <br/>
                σp = standard deviation of the portfolio’s excess return
            </div>
        </>
    )
}