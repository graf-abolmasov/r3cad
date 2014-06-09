package geb.pages.com.r3.dataset

import geb.Page
import geb.remote.services.Services

/**
 * Created by graf on 14/06/14.
 */
final class DataSetViews {

    final static Class<DataSetEditPage> edit = DataSetEditPage

    final static DoubleDataSetViews doubleDataSet = new DoubleDataSetViews()

}

class DataSetEditPage extends Page {

    static content = {
        form { $('form') }
        titleField { form.title }
        railWayField { form.railWay }
    }

    @Override
    String convertToPath(Object... args) {
        assert args.length == 2

        Services.grailsLinkGenerator.link(resource: 'dataSet', action: 'edit', id: args[1], params: [projectId: args[0]])
    }
}


