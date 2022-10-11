import {useEffect, useState} from "react";
import "./TwitterComponent.css"
import {LoadingIconComponent} from "../LoadingIconComponent/LoadingIconComponent";
import {DropdownMenuComponent} from "../DropdownMenuComponent/DropdownMenuComponent";
import Highcharts from 'highcharts'
import PieChart from "highcharts-react-official";
import {SERVER_PATH} from "../../App";

/**
 * Represents the twitter component, that displays the segment analysis and the related tweets
 * @return {JSX.Element} twitter component
 * @constructor
 */
export const TwitterComponent = () => {
    const [sentimentAnalysisContent, setSentimentAnalysisContent] = useState(null);
    const [tweetContent, setTweetContent] = useState(null);
    const [selectedTwitterAccount, setSelectedTwitterAccount] = useState("707515829798182912");

    useEffect(async () => {
        setSentimentAnalysisContent(<LoadingIconComponent/>);
        setTweetContent(<LoadingIconComponent/>)

        await buildSentimentAnalysis();
        await buildTweet();
    }, [])

    /**
     * Called when the user is viewing the tweets of another user
     */
    useEffect(async () => {
        setTweetContent(<LoadingIconComponent/>)

        await buildTweet();
    }, [selectedTwitterAccount])

    /**
     * Builds the part for the tweet display
     * @return {Promise<void>}
     */
    const buildTweet = async () => {
        try {
            // https://tweeterid.com/
            const response = await fetch(SERVER_PATH + '/twitter/tweets/findby/user/' + selectedTwitterAccount);
            const body = await response.json();
            // author_id, created_at, text, id

            const arr = [];

            for (let i = 0; i < Object.keys(body).length; i++) {
                arr.push(
                    <div className="basic-card">
                        <div className="card-title">
                            <span>{body[i].created_at}</span>
                        </div>
                        <div className="card-content">
                            <span>{body[i].text}</span>
                        </div>
                    </div>

                )
            }

            setTweetContent(arr);
        } catch (e) {
            setTweetContent("Error loading tweet data")
        }
    }

    /**
     * Builds the part for the sa display
     * @return {Promise<void>}
     */
    const buildSentimentAnalysis = async () => {
        try {
            const response = await fetch(SERVER_PATH + '/popularitytrend');
            const body = await response.json();

            const options = {
                chart: {
                    type: "pie"
                },
                title: {
                    text: 'Sentiment analysis pie chart'
                },
                tooltip: {
                    pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
                },
                series: [{
                    data: [{
                        name: 'positive',
                        y: body.positive,
                        color: "#008000"
                    },  {
                        name: 'neutral',
                        y: body.neutral,
                        color: "#D3D3D3"
                    },{
                        name: 'negative',
                        y: body.negative,
                        color: "#FF0000"
                    }]
                }]
            };

            setSentimentAnalysisContent(<PieChart highcharts={Highcharts} options={options} />);
        } catch (e) {
            setSentimentAnalysisContent("Error loading segment analysis");
        }

    }

    return (
        <div className="twitter">
            <div>
                <h1>Sentiment analysis</h1>
                {sentimentAnalysisContent}
            </div>

            <hr/>

            <div>
                <h3>Used Tweets</h3>
                <DropdownMenuComponent
                    arr={{"707515829798182912": "@wavesprotocol", "1038127541607956480": "@SignatureChain",
                        "1169574258919522304": "@neutrino_proto", "1142606887": "@sasha35625"}}
                    onChange={setSelectedTwitterAccount}
                />

                <div className="twitter-content">
                    {tweetContent}
                </div>
            </div>
        </div>
    )
}
