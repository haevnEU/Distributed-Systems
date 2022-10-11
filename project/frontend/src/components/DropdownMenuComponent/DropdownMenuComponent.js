import "./DropdownMenuComponent.css"

/**
 * Represents the dropdownMenu component, that displays a dropdown menu
 * @param arr
 * @param onChange
 * @return {JSX.Element}
 * @constructor
 */
export const DropdownMenuComponent = ({arr, onChange}) => {
    /**
     *
     * @return {*[]}
     */
    const build = () => {
        const menu = [];

        for (const key in arr) {
            menu.push(<option value={key}>{arr[key]}</option>);
        }

        return menu;
    }

    return (
        <div className="select-wrapper">
            <select className="twitter-dropdown" id="dropdown" onChange={(event) => onChange(event.target.value)}>
                {build()}
            </select>
        </div>
    )
}