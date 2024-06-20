package org.example.demo.controller;

import org.example.demo.model.Customer;
import org.example.demo.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller//  Annotation này chỉ định class này là một Spring MVC Controller, nghĩa là nó xử lý các yêu cầu HTTP.
@RequestMapping("/customers")//Annotation này ánh xạ các yêu cầu HTTP bắt đầu bằng /customers đến các phương thức trong lớp CustomerController.

public class CustomerController {

@Autowired //Annotation này cho phép Spring tự động tiêm (inject) một instance của ICustomerService vào biến customerService.
    private ICustomerService customerService;

@GetMapping("")//Annotation này ánh xạ các yêu cầu HTTP GET đến đường dẫn /customers (vì đường dẫn gốc của controller là /customers) đến phương thức index.
    public String index(Model model) {
    List<Customer> customersList = customerService.findAll(); //Gọi phương thức findAll để lấy danh sách tất cả các khách hàng.
    model.addAttribute("customers", customersList);
    return "/index";
    }


@GetMapping("/create")
    public String create(Model model) {
    model.addAttribute("customer", new Customer()); //hêm danh sách khách hàng vào model với tên customers.
        return "/create";
    }


@PostMapping("/save")
    public String save(@ModelAttribute Customer customer) {
    System.out.println(customer.getId());
    customerService.save(customer); // Gọi phương thức save của customerService để lưu khách hàng.
    System.out.println(customer.getId());
    System.out.println(customer.getName());
    return "redirect:/customers";
    }

@GetMapping("/{id}/edit")
    public String update(@PathVariable Long id, Model model) {//Khai báo phương thức update trả về một chuỗi. Tham số id được lấy từ đường dẫn URL.
    model.addAttribute("customer", customerService); //Thêm đối tượng Customer vào model với tên customer, tìm thấy bằng customerService.findById(id).
    return "/update";
    }


@PostMapping("/update") // Annotation này ánh xạ các yêu cầu HTTP POST đến đường dẫn /customers/update đến phương thức update.
    public String update(Customer customer) {
    customerService.save(customer);
    return "redirect:/customers"; //Trả về một chuỗi điều hướng để chuyển hướng trình duyệt về đường dẫn /customers.
    }


@GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id, Model model) {
    model.addAttribute("customer", customerService.findById(id));
    return "/delete";
    }


@PostMapping("/delete")
    public String delete(Customer customer, RedirectAttributes redirectAttributes) {
    customerService.remove(customer.getId());
    redirectAttributes.addFlashAttribute("success", "remove customer successfully");
    return "redirect:/customers";
}


@GetMapping("/{id}/view")
    public String view(@PathVariable Long id, Model model) {
    model.addAttribute("customer", customerService.findById(id));
    return "/view";
}


}
