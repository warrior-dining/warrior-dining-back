package warriordiningback.api.code;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import warriordiningback.domain.Code;
import warriordiningback.domain.CodeRepository;
import warriordiningback.exception.DiningApplicationException;
import warriordiningback.exception.ErrorCode;

@Service
@RequiredArgsConstructor
public class CodeService {

    private final CodeRepository codeRepository;

    public Code findCodeById(Long id) {
        return codeRepository.findById(id).orElseThrow(()
                -> new DiningApplicationException(ErrorCode.CODE_NOT_FOUND));
    }
}
