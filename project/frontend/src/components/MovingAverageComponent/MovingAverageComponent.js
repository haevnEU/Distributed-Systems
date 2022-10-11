import Highcharts from 'highcharts'
import HighchartsReact from 'highcharts-react-official'
import {useEffect, useState} from "react";
import {formatDate} from "../../util/DateFormatter";
import {DropdownMenuComponent} from "../DropdownMenuComponent/DropdownMenuComponent";
import "./MovingAverageComponent.css"
import {SERVER_PATH} from "../../App";
import { MathComponent } from 'mathjax-react'
import {LoadingIconComponent} from "../LoadingIconComponent/LoadingIconComponent";

/**
 * Represents the movingAverage component, that displays the calculated moving average and the calculation basis
 * @return {JSX.Element}
 * @constructor
 */
export const MovingAverageComponent = () => {
    const [content, setContent] = useState(null);
    const [selectedDays, setSelectedDays] = useState(5);
    const [lastUpdated, setLastUpdated] = useState(null);

    /**
     * sorts the object and returns the last n elements
     * @param obj
     * @param n
     * @param size
     * @return {{}}
     */
    const lastN = (obj, n, size) => {
        return Object.keys(obj)
            .sort()
            .slice(size-n, size) //get the last N
            .reduce(function(memo, current) {
                memo[current] = obj[current]
                return memo;
            }, {})
    }

    useEffect(async () => {
        setContent(<LoadingIconComponent/>)
        try {
            setContent(await buildMovingAverage());
        } catch (e) {
            setContent("Error loading moving average data");
        }
    }, [selectedDays])

    const buildMovingAverage = async () => {
        // calling the api
        const response = await fetch(SERVER_PATH + '/getAllPricesAndMovingAverage?period=' + selectedDays);
        const body = await response.json();
        const arr = lastN(body.data, selectedDays, Object.keys(body.data).length);

        const movingAverageArr = [];
        const priceEndOfDayArr = [];
        const categoriesArr = [];
        for (const key in arr) {
            const date = key; //formatDate(key);
            movingAverageArr.push([date, arr[key].movingAvg === null ? NaN : arr[key].movingAvg]);
            priceEndOfDayArr.push([date, arr[key].priceEod]);
            categoriesArr.push(parseInt(key));
        }

        setLastUpdated(formatDate(categoriesArr[categoriesArr.length-1]))

        const options = ({
            title: {
                text: 'Moving Average Chart',
                style: {
                    color: '#000',
                    fontSize: '14px',
                    fontWeight: 'bold'
                }
            },
            xAxis: {
                title: {
                    text: 'Day'
                },
                type: "datetime",
                categories: categoriesArr,
                labels: {
                    formatter:function(){
                        return Highcharts.dateFormat('%d.%m.%Y',this.value);
                    }
                }
            },
            yAxis: {
                title: {
                    text: 'le price'
                }
            },
            legend: {
                enabled: true,
                itemStyle: {
                    fontSize: '11px',
                    fontWeight: 'normal'
                }
            },
            tooltip: {
                headerFormat: '{series.name}<br />',
                pointFormat: 'USD${point.y:.2f}', //'{point.name}: USD${point.y:.2f}',
                crosshairs: [true]
            },
            series: [{
                type: "line",
                name: 'Price end of day',
                data: priceEndOfDayArr
            },
                {
                    name: 'Moving Average',
                    data: movingAverageArr,
                }]
        })

        return <HighchartsReact highcharts={Highcharts} options={options}/>
    }

    return (
        <div>
            <h1>Moving Average</h1>
            <div className="moving-average">
                <DropdownMenuComponent arr={{200: "200 Days", 100: "100 Days", 50: "50 Days", 20: "20 Days", 10: "10 Days", 5: "5 Days"}} onChange={setSelectedDays}/>
                Last Updated: {lastUpdated}
            </div>
            <br/>
            {content}

            <hr/>

            <h3>Calculation basis</h3>
            <MathComponent tex={String.raw`SMA = {A1 + A2 + ... + An \over n}`}/>
            where <br/>
            A = Average in period n <br/>
            n = Number of periods
        </div>
    )
}