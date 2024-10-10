package kernel360.techpick.feature.link.model;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import kernel360.techpick.feature.link.exception.ApiLinkException;
import kernel360.techpick.core.model.link.Link;
import kernel360.techpick.feature.link.repository.LinkRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LinkProvider {

	private final LinkRepository linkRepository;

	public Link save(Link link) {
		return linkRepository.save(link);
	}

	public Link findById(Long id) throws ApiLinkException {
		return linkRepository.findById(id).orElseThrow(ApiLinkException::LINK_NOT_FOUND);
	}

	public Link findByUrl(String url) throws ApiLinkException {
		return linkRepository.findByUrl(url).orElseThrow(ApiLinkException::LINK_NOT_FOUND);
	}

	public Optional<Link> findByUrlOrElseGet(String url) {
		return linkRepository.findByUrl(url);
	}

	public List<Link> findAll() {
		return linkRepository.findAll();
	}

	public void deleteById(Long id) throws ApiLinkException {
		linkRepository.deleteById(id);
	}

	public boolean existsByUrl(String url) {
		return linkRepository.existsByUrl(url);
	}
}
