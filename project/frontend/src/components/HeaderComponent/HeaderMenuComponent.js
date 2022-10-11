import React, {useEffect} from 'react'
import "./HeaderMenuComponent.css"

/**
 * The header menu containing the buttons to navigate the site
 * @param menu
 * @param updateContentDisplay
 * @returns {JSX.Element}
 * @constructor
 */
export const HeaderMenuComponent = ({menu, updateActiveContentPage}) => {
    useEffect(() => {
        buttonClicked(Object.keys(menu)[0]);
    }, []);

    /**
     * menu button clicked
     * @param page
     */
    const buttonClicked = (page) => {
        const buttons = document.getElementsByClassName('nav-menu-button');
        for (let i = 0; i < buttons.length; i++) {
            if (buttons[i].value === page) {
                buttons[i].style.backgroundColor = "var(--header-menu-button-active-background)";
                buttons[i].style.color = "var(--header-menu-button-active-text)";
            } else {
                buttons[i].style.backgroundColor = "inherit";
                buttons[i].style.color = "inherit";
            }
        }
        updateActiveContentPage(page);
    }

    /**
     * Creates the menu buttons
     * @return {*[]} array with alle the buttons
     */
    const createMenuButtons = () => {
        let arr = [];
        for (const key in menu) {
            arr.push(
                <button className="nav-menu-button" type="button" value={key}
                             onClick={() => buttonClicked(key)}>
                    {menu[key]}
                </button>
            );
        }
        return arr;
    }

    return (
        <div className="nav">
            {createMenuButtons()}
        </div>
    )
}