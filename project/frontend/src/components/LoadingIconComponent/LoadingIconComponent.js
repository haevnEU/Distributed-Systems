import "./LoadingIconComponent.css"

/**
 * loading icon with an optional text
 * @param text
 * @return {JSX.Element}
 * @constructor
 */
export const LoadingIconComponent = ({text = ""}) => {
    return (
        <div className="lds-ellipsis text-center">
            {text}
            <div></div>
            <div></div>
            <div></div>
            <div></div>
        </div>
    )
}