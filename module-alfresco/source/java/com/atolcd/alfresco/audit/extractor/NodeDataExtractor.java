package com.atolcd.alfresco.audit.extractor;

import java.io.Serializable;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.audit.extractor.AbstractDataExtractor;
import org.alfresco.repo.model.filefolder.FileInfoImpl;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;

public class NodeDataExtractor extends AbstractDataExtractor {
	NodeService nodeService;
	NodeRef node;

	public void setNodeService(NodeService nodeService) {
		this.nodeService = nodeService;
	}

	@Override
	public boolean isSupported(Serializable data) {
		boolean res = false;

		if (data != null) {
			node = null;

			if (data.getClass() == ChildAssociationRef.class) {
				node = ((ChildAssociationRef) data).getChildRef();
			} else if (data.getClass() == FileInfoImpl.class) {
				node = ((FileInfoImpl) data).getNodeRef();
			} else if (data.getClass() == NodeRef.class) {
				node = (NodeRef) data;
			}

			if (node != null && nodeService.getType(node).compareTo(ContentModel.TYPE_CONTENT) == 0)
				res = true;
		}

		return res;
	}

	@Override
	public Serializable extractData(Serializable value) throws Throwable {
		return node;
	}
}
