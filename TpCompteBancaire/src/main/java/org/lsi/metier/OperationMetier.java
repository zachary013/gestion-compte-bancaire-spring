package org.lsi.metier;

import org.lsi.dto.OperationRequest;
import org.lsi.dto.OperationResponse;
import java.util.List;

public interface OperationMetier {
    public OperationResponse saveOperation(OperationRequest request);
    public List<OperationResponse> listOperation();
    public OperationResponse verser(OperationRequest request);
    public OperationResponse retirer(OperationRequest request);
    public OperationResponse virement(OperationRequest request);
    public List<OperationResponse> getOperationsByCompte(String codeCompte);
    public List<OperationResponse> getOperationsByEmploye(Long codeEmploye);
}