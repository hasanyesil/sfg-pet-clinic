package sfgpetclinic.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import sfgpetclinic.model.Owner;
import sfgpetclinic.services.OwnerService;

import javax.print.attribute.standard.PresentationDirection;
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
        List<Owner> results = ownerService.findAllByLastNameLike(owner.getLastName());
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

}
