package kernel360.techpick.feature.structure.model;

import org.springframework.stereotype.Component;

import kernel360.techpick.core.model.folder.StructureJson;
import kernel360.techpick.feature.structure.exception.ApiStructureException;
import kernel360.techpick.feature.structure.repository.StructureJsonRepository;
import kernel360.techpick.feature.structure.service.Structure;
import kernel360.techpick.feature.structure.service.node.server.ServerNode;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StructureJsonProvider {

	private final StructureJsonRepository structureJsonRepository;

	public StructureJson save(StructureJson structureJson) {
		return structureJsonRepository.save(structureJson);
	}

	public StructureJson findByUserId(Long userId) {
		return structureJsonRepository.findByUserId(userId)
			.orElseThrow(ApiStructureException::USER_STRUCTURE_JSON_NOT_FOUND);
	}

	public void updateStructureJsonByUserIdAndStructure(Long userId, Structure<ServerNode> structure) {
		StructureJson structureJson = this.findByUserId(userId);
		structureJson.updateStructure(structure.serialize());
		this.save(structureJson);
	}
}
