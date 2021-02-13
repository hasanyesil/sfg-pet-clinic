package sfgpetclinic.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import sfgpetclinic.model.Owner;
import sfgpetclinic.services.OwnerService;

import javax.print.attribute.standard.PresentationDirection;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RequestMapping("/owners")
@Controller
public class OwnerController {

    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @InitBinder //Dont allow to webforms sending id fields. It gonna be handled by database
    public void setAllowedFields(WebDataBinder dataBinder){
        dataBinder.setDisallowedFields("id");
    }

    @RequestMapping({"/find"})
    public String findOwners(Model model){
        model.addAttribute("owner", Owner.builder().build());
        return "owners/findOwners";
    }

    @GetMapping
    public String processFindForm(Owner owner, BindingResult result, Model model){
        // allow parameterless GET request for /owners to return all recors.
        if(owner.getLastName() == null){
            owner.setLastName("");
        }

        //find owners by last name
        List<Owner> results = ownerService.findAllByLastNameLike("%" + owner.getLastName() + "%");
        if(results.isEmpty()){
            // no owner found
            result.rejectValue("lastName", "notFound", "not found");
            return "owners/findOwners";
        }else if(results.size() == 1){
            // 1 owner found
            owner = results.iterator().next();
            return "redirect:/owners/" + owner.getId();
        }else{
            // multiple owners found
            model.addAttribute("selections",results);
            return "owners/ownersList";
        }
    }

    @GetMapping("/{ownerId}")
    public ModelAndView showOwner(@PathVariable("ownerId") Long ownerId){
        ModelAndView mav = new ModelAndView("owners/ownerDetails");
        mav.addObject(ownerService.findById(ownerId));
        return mav;
    }

    @GetMapping("/new")
    public String initCreationForm(Model model){
        model.addAttribute("owner",Owner.builder().build());
        return "owners/createOrUpdateOwnerForm";
    }

    @PostMapping("/new")
    public String processCreationForm(@Valid Owner owner, BindingResult result){
        if(result.hasErrors()){
            return "owners/createOrUpdateOwnerForm";
        }else{
            Owner savedOwner = ownerService.save(owner);
            return "redirect:/owners/" + savedOwner.getId();
        }
    }

    @GetMapping("/{ownerId}/edit")
    public String initUpdateOwnerForm(@PathVariable("ownerId") Long ownerId, Model model){
        model.addAttribute("owner",ownerService.findById(ownerId));
        return "owners/createOrUpdateOwnerForm";
    }

    @PostMapping("/{ownerId}/edit")
    public String processUpdateOwnerForm(@Valid Owner owner,@PathVariable("ownerId") Long ownerId, BindingResult result){
        if(result.hasErrors()){
            return "owners/createOrUpdateOwnerForm";
        }

        owner.setId(ownerId);
        Owner updatedOwner = ownerService.save(owner);
        return "redirect:/owners/" + updatedOwner.getId();
    }
}
