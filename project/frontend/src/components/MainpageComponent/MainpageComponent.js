import {WavesPriceComponent} from "../WavesPriceComponent/WavesPriceComponent";

/**
 * Represents the mainpage component, that displays a description of the project with all participants
 * @return {JSX.Element} mainpage component
 */
export const MainpageComponent = () => {
    return (
        <>
            <WavesPriceComponent/> <br/>
            <h1>Mainpage</h1>

            <p>Final project in Modul Verteilte Systeme in WS21/22</p>
            <p>by Group 4:</p>
            <p>Jerome, Nils, Maximilian, Gereon, Dennis</p>


            <br/><br/><br/><br/><br/>
            <a href="https://www.flaticon.com/free-icons/duck" title="duck icons">Duck icons created by pongsakornRed - Flaticon</a>
        </>
    )
}