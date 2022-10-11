import React from 'react';
import {HeaderMenuComponent} from "./HeaderMenuComponent";
import "./HeaderComponent.css"

/**
 * the header containing the menu
 * @param updateContentDisplay function
 * @return {JSX.Element}
 */
export const HeaderComponent = ({menu, updateActiveContentPage}) => {
    return (
        <div className="header overflow-auto">
            <div className="flex header-nav overflow-hidden">
                <HeaderMenuComponent menu={menu}
                                     updateActiveContentPage={updateActiveContentPage}/>
            </div>
        </div>
    )
}