package com.project.chamjimayo.service;

import com.project.chamjimayo.controller.dto.request.GoogleInAppPurchaseRequest;
import com.project.chamjimayo.controller.dto.PointChangeDto;
import com.project.chamjimayo.repository.domain.entity.Product;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class InAppPurchaseService {
  private final UserService userService;
  private final OrderService orderService;
  private final ReceiptValidationService receiptValidationService;

  @Transactional
  public PointChangeDto verifyPurchase(Long userId, GoogleInAppPurchaseRequest request) {
    if (receiptValidationService.validateReceipt(request)) {
      Integer point = Product.pointsFromProductId(request.getProductId());
      PointChangeDto pointChangeDto = userService.chargePoints(
          PointChangeDto.create(userId, point));

      orderService.createOrder(request.getToken(), pointChangeDto.getUserId(),
          pointChangeDto.getPoint());

      return pointChangeDto;
    }

    return PointChangeDto.create(userId, 0);
  }
}
