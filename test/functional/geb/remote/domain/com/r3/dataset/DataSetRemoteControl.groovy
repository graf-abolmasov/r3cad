package geb.remote.domain.com.r3.dataset

import com.r3.Project
import com.r3.dataset.BaseDataSet
import geb.remote.domain.DomainRemoteControl

/**
 * Created by graf on 14/06/14.
 */
class DataSetRemoteControl extends DomainRemoteControl {

    DataSetRemoteControl() {
        super(BaseDataSet)
    }

    long findByTitleAndProject(String title, long projectId) {
        remote.exec {
            BaseDataSet.findByTitleAndProject(title, Project.read(projectId)).id
        }
    }
}
