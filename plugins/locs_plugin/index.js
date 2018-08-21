
import {ManagedTable, Text, Panel, colors, FlexRow, DataInspector} from 'sonar';
import {SonarPlugin} from 'sonar';

const {clone} = require('lodash');

type receivedData = {time: string};

export type DataList = {
  [name: string]: any,
};

type State = {|
  dataList: ?DataList,
  changesList: Array<receivedData>,
|};

export default class extends SonarPlugin<State> {
  static title = 'Hoang Loc';
  static id = 'locsplugin';

  state = {
    dataList: null,
  };

  reducers = {
    UpdateData(state: State, results: Object) {
      return {
        dataList: results.results,
      };
    },
  };

  init() {
    this.client.subscribe(
      'randomDataUpdate',
      (results: receivedData) => {this.dispatchAction({results, type: 'UpdateData'});},
    );
  }

 render() {
    if (this.state.dataList == null) {
      return null;
    }
    return (
			   <DataInspector
			            data={this.state.dataList}
			            setValue={null}
			    />

    );
  }
}
