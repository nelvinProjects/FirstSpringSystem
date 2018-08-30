package com.qa.springDatabase.controller;

import javax.validation.Valid;

import org.apache.tomcat.util.modeler.modules.ModelerSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.qa.springDatabase.exception.ResourceNotFoundException;
import com.qa.springDatabase.model.Order;
import com.qa.springDatabase.repository.OrderRepository;
import com.qa.springDatabase.repository.SpringBootRepository;

@RestController
public class PersonController {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private SpringBootRepository sRepository;

	@GetMapping("/person/{id}/orders")
	public Page<Order> getAllOrdersByPersonID(@PathVariable(value = "id") Long id, Pageable pageable) {
		return orderRepository.findByPersonId(id, pageable);
	}

	@PostMapping("/person/{id}/orders")
	public Order createComment(@PathVariable(value = "id") Long id, @Valid @RequestBody Order order) {
		return sRepository.findById(id).map(model -> {
			order.setPerson(model);
			return orderRepository.save(order);
		}).orElseThrow(() -> new ResourceNotFoundException("Person", "id", order));
	}

	@PutMapping("/person/{id}/orders/{orderid}")
	public Order updateOrder(@PathVariable(value = "id") long id, @PathVariable(value = "orderid") Long orderid,
			@Valid @RequestBody Order orderReq) {
		if (!sRepository.existsById(id)) {
			throw new ResourceNotFoundException("Person ", "id", orderReq);
		}

		return orderRepository.findById(orderid).map(order -> {
			order.setTitle(orderReq.getTitle());
			return orderRepository.save(order);
		}).orElseThrow(() -> new ResourceNotFoundException("OrderId", "id", orderReq));
	}

	@DeleteMapping("/person/{id}/orders/{orderid}")
	public ResponseEntity<?> deleteComment(@PathVariable(value = "id") Long id,
			@PathVariable(value = "orderid") Long orderid) {
		if (!sRepository.existsById(id)) {
			throw new ResourceNotFoundException("Person", "id", id);
		}

		return orderRepository.findById(orderid).map(order -> {
			orderRepository.delete(order);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new ResourceNotFoundException("Order ID", orderid.toString(), null));
	}
}
