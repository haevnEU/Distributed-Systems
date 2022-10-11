import "./TableComponent.css"

/**
 * Represents the table component, that displays a simple table
 * @param data row data
 * @param header header keys
 * @return {JSX.Element}
 * @constructor
 */
export const TableComponent = ({data, header}) => {
    /**
     * Builds the table
     * @param data
     * @param headerArr
     * @return {JSX.Element}
     */
    const buildTable = (data, headerArr) => {
        const rows = generateRows(data, headerArr);

        return (
            <table className="table full-width text-center">
                <tr className="table-header">
                    <th>Asset</th>
                    {generateHeader(headerArr)}
                </tr>

                    {rows.map((row) =>
                        <>{row}</>
                    )}
            </table>
        )
    }

    /**
     * Builds the headers
     * @param headerArr
     * @return {*[]}
     */
    const generateHeader = (headerArr) => {
        const arr = [];

        for (let i = 0; i < headerArr.length; i++) {
            arr.push(<th>{headerArr[i]}</th>)
        }

        return arr;
    }

    /**
     * Builds the rows
     * @param data
     * @param headerArr
     * @return {*[]}
     */
    const generateRows = (data, headerArr) => {
        const rows = [];

        for (const key in data) {
            rows.push(<tr className="table-row">{generateRow(data[key], key, headerArr)}</tr>);
        }
        return rows;
    }

    /**
     * Builds the row
     * @param data
     * @param title
     * @param headerArr
     * @return {*[]}
     */
    const generateRow = (data, title, headerArr) => {
        const row = [];

        row.push(<td>{title}</td>)
        for (let i = 0; i < headerArr.length; i++) {
            if (data[headerArr[i]] !== undefined) {
                row.push(<td>{data[headerArr[i]]}%</td>)
            } else {
                row.push(<td>-</td>)
            }
        }

        return row;
    }

    return (
        <>
            {buildTable(data, header)}
        </>
    )
}
