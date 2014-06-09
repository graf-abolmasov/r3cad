package geb.pages.com.r3.dataset

/**
 * Created by graf on 14/06/14.
 */
class DoubleDataSetViews {

    final static Class<DoubleDataSetEditPage> edit = DoubleDataSetEditPage

}

class DoubleDataSetEditPage extends DataSetEditPage {

    static content = {
        unitsField { form.units }
    }

}