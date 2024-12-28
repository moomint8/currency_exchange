package moomint.toy.currency_exchange.account.controller;

import jakarta.validation.Valid;
import moomint.toy.currency_exchange.account.domain.aggregate.vo.TransactionRequestVO;
import moomint.toy.currency_exchange.account.domain.aggregate.vo.TransactionResponseVO;
import moomint.toy.currency_exchange.account.dto.TransactionDTO;
import moomint.toy.currency_exchange.account.dto.TransactionResultDTO;
import moomint.toy.currency_exchange.account.service.TransactionService;
import moomint.toy.currency_exchange.common.Exception.InvalidCurrencyException;
import moomint.toy.currency_exchange.common.Exception.NotAccountOwnerException;
import moomint.toy.currency_exchange.common.Exception.NotLoggedInException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping()
    public ResponseEntity<TransactionResponseVO> transaction(@Valid @RequestBody TransactionRequestVO request) {

        TransactionResponseVO responseVO;

        try {

            TransactionDTO requestDTO = new TransactionDTO(request.getTransactionType(), request.getAccountNo(),
                    request.getCurrencyCode(), request.getAmount());

            TransactionResultDTO resultDTO = selectTransaction(requestDTO);

            responseVO = TransactionResponseVO.builder()
                    .message("거래 성공")
                    .transactionType(Objects.requireNonNull(resultDTO).transactionType())
                    .accountNo(resultDTO.accountNo())
                    .currencyCode(resultDTO.currency())
                    .amount(resultDTO.amount())
                    .remainingBalance(resultDTO.remainingBalance())
                    .build();

            return ResponseEntity.ok().body(responseVO);

        } catch (NotLoggedInException e) {
            responseVO = TransactionResponseVO.builder()
                    .message(e.getMessage())
                    .build();
            return ResponseEntity.status(401).body(responseVO);
        } catch (NotAccountOwnerException e) {
            responseVO = TransactionResponseVO.builder()
                    .message(e.getMessage())
                    .build();
            return ResponseEntity.status(403).body(responseVO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    private TransactionResultDTO selectTransaction(TransactionDTO request) throws NotAccountOwnerException, NotLoggedInException, InvalidCurrencyException {

        switch (request.transactionType()) {
            case DEPOSIT -> {
                return transactionService.deposit(request);
            }
            case WITHDRAWAL, EXCHANGE -> {
                return null;
            }
            default -> throw new IllegalArgumentException("Invalid transaction type");
        }
    }
}
