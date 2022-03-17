import './App.css';
import {useEffect, useState} from "react";
import {Line} from "react-chartjs-2";

const WS_URL = "ws://localhost:2100/market/api/v1/trades";

function App() {
    let [trades, setTrades] = useState([]);
    let [rowSize, setRowSize] = useState(5);
    let [wsUrl, setWsUrl] = useState("ws://localhost:2100/market/api/v1/trades");
    let [totalVolume, setTotalVolume] = useState(0.);
    let [chartModel, setChartModel] = useState({ // charjs model
        labels: [],
        datasets: [
            {
                label: 'MSFT',
                fill: false,
                lineTension: 0.1,
                backgroundColor: 'rgba(75,192,192,0.4)',
                borderColor: 'rgba(75,192,192,1)',
                borderCapStyle: 'butt',
                borderDash: [],
                borderDashOffset: 0.0,
                borderJoinStyle: 'miter',
                pointBorderColor: 'rgba(75,192,192,1)',
                pointBackgroundColor: '#fff',
                pointBorderWidth: 1,
                pointHoverRadius: 5,
                pointHoverBackgroundColor: 'rgba(75,192,192,1)',
                pointHoverBorderColor: 'rgba(220,220,220,1)',
                pointHoverBorderWidth: 2,
                pointRadius: 1,
                pointHitRadius: 10,
                data: []
            }
        ]
    });
    let webSocket;

    function handleChange(event) {
        setRowSize(Number(event.target.value));
    }

    function handleServerChange(event) {
        setWsUrl(event.target.value);
    }

    useEffect(() => {
        webSocket = new WebSocket(wsUrl);
        webSocket.onopen = () => {

            webSocket.onmessage = (payload) => {
                let trade = JSON.parse(payload.data);
                let myTrades = [...trades];
                trade.volume = Number(trade.price) * Number(trade.quantity);
                myTrades.push(trade);
                if (myTrades.length > Number(rowSize)) {
                    let index = myTrades.length - Number(rowSize);
                    myTrades = myTrades.splice(index, Number(rowSize));
                }
                setTrades(myTrades);
                setTotalVolume(totalVolume + trade.volume);
                let myChartModel = {...chartModel};
                myChartModel.datasets = [...chartModel.datasets];
                myChartModel.labels = [...chartModel.labels];
                myChartModel.labels.push('1');
                myChartModel.datasets[0].data = [...chartModel.datasets[0].data];
                myChartModel.datasets[0].data.push(trade.volume);
                if (myChartModel.datasets[0].data.length > Number(rowSize)) {
                    let index = myChartModel.datasets[0].data.length - Number(rowSize);
                    myChartModel.datasets[0].data = myChartModel.datasets[0].data.splice(index, Number(rowSize));
                }
                if (myChartModel.labels.length > Number(rowSize)) {
                    let index = myChartModel.labels.length - Number(rowSize);
                    myChartModel.labels = myChartModel.labels.splice(index, Number(rowSize));
                }
                setChartModel(myChartModel);
            };
        };
        return () => {
            webSocket.close();
        };
    }, [trades, totalVolume, rowSize,chartModel]);
    return (
        <div className="container">
            <div className="card">
                <div className="card-header">
                    <h4 className="card-title">Market Data</h4>
                    <span className="badge alert-success">{totalVolume}</span>
                    <select name="" value={rowSize} onChange={handleChange} className="form-select">
                        <option label="5" value="5">5</option>
                        <option label="10" value="10">10</option>
                        <option label="25" value="25">25</option>
                    </select>
                    <select name="wsUrl" value={wsUrl} onChange={handleServerChange} className="form-select">
                        <option>ws://localhost:2100/market/api/v1/trades</option>
                        <option>ws://localhost:2200/market/api/v1/trades</option>
                    </select>

                </div>
                <div className="card-body">
                    <table className="table table-striped table-hover table-bordered table-responsive">
                        <thead>
                        <tr>
                            <th>No</th>
                            <th>ID</th>
                            <th>Symbol</th>
                            <th>Price</th>
                            <th>Quantity</th>
                            <th>Volume</th>
                        </tr>
                        </thead>
                        <tbody>
                        {
                            trades.map((trade, index) => <tr key={trade.tradeId}>
                                <td>{index + 1}</td>
                                <td>{trade.tradeId}</td>
                                <td>{trade.symbol}</td>
                                <td>{trade.price}</td>
                                <td>{trade.quantity}</td>
                                <td>{trade.volume}</td>
                            </tr>)
                        }
                        </tbody>
                    </table>
                </div>
            </div>
            <p></p>
            <div className="card">
                <div className="card-header">
                    <h4 className="card-title">Market Volume</h4>
                </div>
                <div className="card-body">
                    <Line data={chartModel}
                          redraw
                          width={640}
                          height={480}
                          options={{maintainAspectRatio: true, animation: true}}></Line>
                </div>
            </div>
        </div>
    );
}

export default App;
