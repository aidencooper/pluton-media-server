package net.aidencooper.pluton.mediaserver.media.domain.service;

import net.aidencooper.pluton.mediaserver.media.domain.model.*;
import net.aidencooper.pluton.mediaserver.media.domain.model.view.EnrichedShow;
import net.aidencooper.pluton.mediaserver.media.domain.model.view.ShowMetadata;
import org.springframework.stereotype.Service;

@Service
public class MediaEnrichmentService {
    public EnrichedShow enrich(Show show) {
        return new EnrichedShow(
                show,
                new ShowMetadata(
                        this.resolveTitle(show),
                        this.resolveDisplayTitle(show)
                )
        );
    }

    private Title resolveTitle(Show show) {
        return Title.of("Unknown");
    }

    private DisplayTitle resolveDisplayTitle(Show show) {
        DisplayTitle.of("Show-" + show.id().toString().substring(0, 8));
    }
}
