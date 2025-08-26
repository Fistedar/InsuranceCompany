package org.javaguru.travel.insurance.web.v2;

import jakarta.validation.Valid;
import org.javaguru.travel.insurance.core.api.command.TravelCalculatePremiumCoreCommand;
import org.javaguru.travel.insurance.core.api.command.TravelCalculatePremiumCoreResult;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.service.TravelCalculatePremiumService;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.javaguru.travel.insurance.dto.v2.AgreementMapperV2;
import org.javaguru.travel.insurance.dto.v2.ResponseMapperV2;
import org.javaguru.travel.insurance.dto.v2.TravelCalculatePremiumRequestV2;
import org.javaguru.travel.insurance.dto.v2.TravelCalculatePremiumResponseV2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.List;

@Controller
public class TravelInsuranceControllerV2 {

    private final TravelCalculatePremiumService service;
    private final AgreementMapperV2 agreementMapper;
    private final ResponseMapperV2 responseMapper;

    TravelInsuranceControllerV2(TravelCalculatePremiumService service, AgreementMapperV2 agreementMapper, ResponseMapperV2 responseMapper) {
        this.service = service;
        this.agreementMapper = agreementMapper;
        this.responseMapper = responseMapper;
    }

    @GetMapping("/insurance/travel/web/v2")
    public String showForm(Model model) {
        model.addAttribute("request", new TravelCalculatePremiumRequestV2());
        model.addAttribute("response", new TravelCalculatePremiumResponseV2());
        return "travel-calculate-premium-v2";
    }

    @PostMapping("/insurance/travel/web/v2")
    public String processForm(@Valid @ModelAttribute(value = "request") TravelCalculatePremiumRequestV2 request,
                              BindingResult bindingResult,
                              Model model) {

        if (bindingResult.hasErrors()) {
            TravelCalculatePremiumResponseV2 response = new TravelCalculatePremiumResponseV2();
            List<ValidationError> errors = bindingResult.getAllErrors().stream()
                    .map(error -> new ValidationError(error.getCode(), error.getDefaultMessage()))
                    .toList();
            response.setErrors(errors);

            model.addAttribute("response", response);
            return "travel-calculate-premium-v2";
        }

        TravelCalculatePremiumResponseV2 response = convertCoreResult(request);
        model.addAttribute("response", response);
        return "travel-calculate-premium-v2";
    }

    @PostMapping("/insurance/travel/web/v2/clear")
    public String clearForm() {
        return "redirect:/insurance/travel/web/v2";
    }


    private TravelCalculatePremiumResponseV2 convertCoreResult(TravelCalculatePremiumRequestV2 request) {
        AgreementDTO agreementDTO = agreementMapper.buildAgreementDTO(request);
        TravelCalculatePremiumCoreCommand command = new TravelCalculatePremiumCoreCommand(agreementDTO);
        TravelCalculatePremiumCoreResult result = service.calculatePremium(command);
        return responseMapper.buildResponse(result);
    }

}
