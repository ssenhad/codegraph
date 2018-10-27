/*
 * Copyright (C) 2015-2018 Diego Feitosa [dnfeitosa@gmail.com]
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
import React from 'react';

const Section = (props) => {
    return (
        <div className="cgr-section">
            {props.header && (
                <div className="cgr-text-heading text-dark">
                    {props.header}
                </div>
            )}
            {props.children}
        </div>
    );
};

// const Section = (props) => {
//     return (
//         <div className="cgr-content-section">
//             {props.header && (
//                 <div className="cgr-text-sub-heading">{props.header}</div>
//             )}
//             {props.children}
//         </div>
//     );
// };

// Section.Section = Section;
//
// export { Section };
export default Section;
