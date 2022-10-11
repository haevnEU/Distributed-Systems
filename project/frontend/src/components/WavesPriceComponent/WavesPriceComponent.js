import {useEffect, useState} from "react";
import {LoadingIconComponent} from "../LoadingIconComponent/LoadingIconComponent";
import {SERVER_PATH} from "../../App";

/**
 * Represents the wavesPrice component, that displays the current waves price in usd-n
 * @return {JSX.Element}
 * @constructor
 */
export const WavesPriceComponent = () => {
    const [content, setContent] = useState(null);

    useEffect(async () => {
        setContent(<LoadingIconComponent/>)

        try {
            // calling the api
            const response = await fetch(SERVER_PATH + '/getCurrentPrice');
            const body = await response.json();
            setContent("1 WAVES = " + parseFloat(body).toFixed(2) + "USD-N");
        } catch (e) {
            setContent("Error loading the current price");
        }
    }, [])

    return (
        <div className="float-left">
            {content}
        </div>
    )
}
