package by.grechishnikov.messenger.attachment.service;

import by.grechishnikov.messenger.attachment.entity.Attachment;
import by.grechishnikov.messenger.attachment.repository.AttachmentRepository;
import by.grechishnikov.messenger.common.service.AbstractServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author - Evgeniy Grechishnikov
 */
@Service
public class AttachmentServiceImpl extends AbstractServiceImpl<Attachment> implements AttachmentService {


    @Autowired
    public AttachmentServiceImpl(AttachmentRepository attachmentRepository) {
        super(attachmentRepository);
    }

}
