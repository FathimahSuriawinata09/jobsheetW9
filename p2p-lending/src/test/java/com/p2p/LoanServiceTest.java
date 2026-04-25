package com.p2p;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.p2p.domain.Borrower;
import com.p2p.domain.Loan;
import com.p2p.service.LoanService;
import java.math.BigDecimal;
public class LoanServiceTest {
    @Test
    void shouldRejectLoanWhenBorrowerNotVerified(){

    // =====================================================
    // SCENARIO:
    // Borrower tidak terverifikasi (KYC = false)
    // Ketika borrower mengajukan pinjaman
    // Maka sistem harus menolak dengan melempar exception
    // =====================================================

    // =========================
    // Arrange (Initial Condition)
    // =========================
    // Borrower belum lolos proses KYC
    Borrower borrower = new Borrower(false, 700);

    //Service untuk =pengajuan loan
    LoanService loanService = new LoanService();

    //Jumlah pinjaman valid
    BigDecimal amount = BigDecimal.valueOf(1000);

    //==========================
    //Act (Action)
    //==========================
    //Borrower mencoba mengajukan loan
        assertThrows(IllegalArgumentException.class, () -> {loanService.createLoan(borrower, amount);
        });
    }

    @Test
    void shouldRejectLoanWhenAmountIsZeroOrNegative(){
        Borrower borrower = new Borrower(true, 700);
        LoanService loanService = new LoanService();
        BigDecimal amount = BigDecimal.valueOf(0);

        assertThrows(IllegalArgumentException.class, () -> {
            loanService.createLoan(borrower, amount);
        });
    }

    @Test
    void shouldApproveLoanWhenCreditScoreHigh(){
        Borrower borrower = new Borrower(true, 700);
        LoanService loanService = new LoanService();
        BigDecimal amount = BigDecimal.valueOf(1000);

        Loan loan = loanService.createLoan(borrower, amount);

        assertEquals(Loan.Status.APPROVED, loan.getStatus());
    }

    @Test
    void shouldRejectLoanWhenCreditScoreLow(){
        Borrower borrower = new Borrower(true, 400);
        LoanService loanService = new LoanService();
        BigDecimal amount = BigDecimal.valueOf(1000);

        Loan loan = loanService.createLoan(borrower, amount);

        assertEquals(Loan.Status.REJECTED, loan.getStatus());
    }
}

