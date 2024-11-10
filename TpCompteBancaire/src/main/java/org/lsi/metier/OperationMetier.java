package org.lsi.metier;

import org.lsi.dto.OperationRequest;
import org.lsi.dto.OperationResponse;
import java.util.List;

public interface OperationMetier {
    OperationResponse saveOperation(OperationRequest request);
    List<OperationResponse> listOperation();
    OperationResponse verser(OperationRequest request);
    OperationResponse retirer(OperationRequest request);
    OperationResponse virement(OperationRequest request);
    List<OperationResponse> getOperationsByCompte(String codeCompte);
}