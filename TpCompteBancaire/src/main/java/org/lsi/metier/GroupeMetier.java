package org.lsi.metier;
import org.lsi.dto.GroupeRequest;
import org.lsi.dto.GroupeResponse;
import org.lsi.entities.Groupe;
import java.util.List;

public interface GroupeMetier {
    GroupeResponse saveGroupe(GroupeRequest groupe);
    List<GroupeResponse> listGroupes();
    GroupeResponse getGroupe(Long id);
    GroupeResponse updateGroupe(Long id, GroupeRequest newGroupeData);
    void deleteGroupe(Long id);
}