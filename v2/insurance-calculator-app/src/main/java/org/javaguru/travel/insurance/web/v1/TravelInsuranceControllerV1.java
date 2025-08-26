package org.javaguru.travel.insurance.web.v1;

import jakarta.validation.Valid;
import org.javaguru.travel.insurance.core.api.command.TravelCalculatePremiumCoreCommand;
import org.javaguru.travel.insurance.core.api.command.TravelCalculatePremiumCoreResult;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.service.TravelCalculatePremiumService;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.javaguru.travel.insurance.dto.v1.AgreementMapperV1;
import org.javaguru.travel.insurance.dto.v1.ResponseMapperV1;
import org.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
import org.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumResponseV1;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.List;

@Controller
public class TravelInsuranceControllerV1 {

    private final TravelCalculatePremiumService service;
    private final AgreementMapperV1 agreementMapper;
    private final ResponseMapperV1 responseMapper;

    TravelInsuranceControllerV1(TravelCalculatePremiumService service, AgreementMapperV1 agreementMapper, ResponseMapperV1 responseMapper) {
        this.service = service;
        this.agreementMapper = agreementMapper;
        this.responseMapper = responseMapper;
    }

    @GetMapping("/insurance/travel/web/v1")
    public String showForm(Model model) {
        model.addAttribute("request", new TravelCalculatePremiumRequestV1());
        model.addAttribute("response", new TravelCalculatePremiumResponseV1());
        return "travel-calculate-premium-v1";
    }

    @PostMapping("/insurance/travel/web/v1")
    public String processForm(@Valid @ModelAttribute(value = "request") TravelCalculatePremiumRequestV1 request,
                              BindingResult bindingResult,
                              Model model) {

        if (bindingResult.hasErrors()) {
            TravelCalculatePremiumResponseV1 response = new TravelCalculatePremiumResponseV1();
            List<ValidationError> errors = bindingResult.getAllErrors().stream()
                    .map(error -> new ValidationError(error.getCode(), error.getDefaultMessage()))
                    .toList();
            response.setErrors(errors);
            model.addAttribute("response", response);
            return "travel-calculate-premium-v1";
        }

        TravelCalculatePremiumResponseV1 response = convertCoreResult(request);
        model.addAttribute("response", response);
        return "travel-calculate-premium-v1";
    }

    @PostMapping("/insurance/travel/web/v1/clear")
    public String clearForm() {
        return "redirect:/insurance/travel/web/v1";
    }

    private TravelCalculatePremiumResponseV1 convertCoreResult(TravelCalculatePremiumRequestV1 request) {
        AgreementDTO agreementDTO = agreementMapper.buildAgreementDTO(request);
        TravelCalculatePremiumCoreCommand command = new TravelCalculatePremiumCoreCommand(agreementDTO);
        TravelCalculatePremiumCoreResult result = service.calculatePremium(command);
        return responseMapper.buildResponse(result);
    }

}
