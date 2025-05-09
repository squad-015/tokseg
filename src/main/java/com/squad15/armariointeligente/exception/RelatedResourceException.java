package com.squad15.armariointeligente.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class RelatedResourceException extends RuntimeException {
	public RelatedResourceException(String message) {
        super(message);
    }
    
    public RelatedResourceException(String resourceName, String relatedResourceName) {
        super(String.format("Não é possível excluir %s pois existem %s vinculados a ele", resourceName, relatedResourceName));
    }

}
