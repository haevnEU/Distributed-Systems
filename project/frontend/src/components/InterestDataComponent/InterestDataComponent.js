import {useEffect, useState} from "react";
import {LoadingIconComponent} from "../LoadingIconComponent/LoadingIconComponent";
import {TableComponent} from "../TableComponent/TableComponent";
import {SERVER_PATH} from "../../App";

/**
 * Represents the interestData component, that displays the current apy of different coins
 * @return {JSX.Element}
 * @constructor
 */
export const InterestDataComponent = () => {
    const [content, setContent] = useState(null);

    useEffect(async () => {
        setContent(<LoadingIconComponent/>)

        try {
            // calling the api
            const response = await fetch(SERVER_PATH + '/getInterest');
            const body = await response.json();

            setContent(buildTable(body));
        } catch (e) {
            setContent("Error loading the apy data");
        }

    }, [])

    /**
     * builds the table with the data
     * @param data
     * @return {JSX.Element}
     */
    const buildTable = (data) => {
        return <TableComponent data={data} header={["last", "3d", "30d", "60d"]}/>;
    }

    return (
        <>
            <h1>APY</h1>
            {content}
        </>
    )
}