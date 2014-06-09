package geb.remote.domain

import com.r3.Organization
import com.r3.Project
import geb.remote.domain.com.r3.dataset.DataSetRemoteControl

/**
 * Created by graf on 14/06/14.
 */
final class Domains {

    final static DomainRemoteControl project = new DomainRemoteControl(Project)

    final static DomainRemoteControl organization = new DomainRemoteControl(Organization)

    final static DataSetRemoteControl dataSet = new DataSetRemoteControl()
}
