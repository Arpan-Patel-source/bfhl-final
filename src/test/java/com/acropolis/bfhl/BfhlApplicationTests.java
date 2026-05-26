package com.acropolis.bfhl;

import com.acropolis.bfhl.dto.BfhlRequest;
import com.acropolis.bfhl.dto.BfhlResponse;
import com.acropolis.bfhl.service.BfhlService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BfhlApplicationTests {

    @Autowired MockMvc mockMvc;
    @Autowired BfhlService bfhlService;

    // ── Unit Tests (service layer) ────────────────────────────────────────────

    @Test
    void exampleA_mixedInput() {
        // spec example A: ["a","1","334","4","R","$"]
        BfhlResponse r = bfhlService.processData(
                new BfhlRequest(List.of("a", "1", "334", "4", "R", "$")));

        assertThat(r.isSuccess()).isTrue();
        assertThat(r.getOddNumbers()).containsExactly("1");
        assertThat(r.getEvenNumbers()).containsExactly("334", "4");
        assertThat(r.getAlphabets()).containsExactly("A", "R");
        assertThat(r.getSpecialCharacters()).containsExactly("$");
        assertThat(r.getSum()).isEqualTo("339");
        assertThat(r.getConcatString()).isEqualTo("Ra");
    }

    @Test
    void exampleB_moreItems() {
        // spec example B: ["2","a","y","4","&","-","*","5","92","b"]
        BfhlResponse r = bfhlService.processData(
                new BfhlRequest(List.of("2", "a", "y", "4", "&", "-", "*", "5", "92", "b")));

        assertThat(r.isSuccess()).isTrue();
        assertThat(r.getOddNumbers()).containsExactly("5");
        assertThat(r.getEvenNumbers()).containsExactly("2", "4", "92");
        assertThat(r.getAlphabets()).containsExactly("A", "Y", "B");
        assertThat(r.getSpecialCharacters()).containsExactly("&", "-", "*");
        assertThat(r.getSum()).isEqualTo("103");
        assertThat(r.getConcatString()).isEqualTo("ByA");
    }

    @Test
    void exampleC_onlyAlphabets() {
        // spec example C: ["A","ABCD","DOE"]
        BfhlResponse r = bfhlService.processData(
                new BfhlRequest(List.of("A", "ABCD", "DOE")));

        assertThat(r.isSuccess()).isTrue();
        assertThat(r.getOddNumbers()).isEmpty();
        assertThat(r.getEvenNumbers()).isEmpty();
        assertThat(r.getAlphabets()).containsExactly("A", "ABCD", "DOE");
        assertThat(r.getSpecialCharacters()).isEmpty();
        assertThat(r.getSum()).isEqualTo("0");
        assertThat(r.getConcatString()).isEqualTo("EoDdCbAa");
    }

    @Test
    void emptyDataArray() {
        BfhlResponse r = bfhlService.processData(new BfhlRequest(List.of()));
        assertThat(r.isSuccess()).isTrue();
        assertThat(r.getOddNumbers()).isEmpty();
        assertThat(r.getEvenNumbers()).isEmpty();
        assertThat(r.getAlphabets()).isEmpty();
        assertThat(r.getSpecialCharacters()).isEmpty();
        assertThat(r.getSum()).isEqualTo("0");
        assertThat(r.getConcatString()).isEmpty();
    }

    @Test
    void onlyNumbers_evenOddSplit() {
        BfhlResponse r = bfhlService.processData(
                new BfhlRequest(List.of("2", "3", "10", "7")));
        assertThat(r.getEvenNumbers()).containsExactly("2", "10");
        assertThat(r.getOddNumbers()).containsExactly("3", "7");
        assertThat(r.getSum()).isEqualTo("22");
        assertThat(r.getConcatString()).isEmpty();
    }

    @Test
    void onlySpecialCharacters() {
        BfhlResponse r = bfhlService.processData(
                new BfhlRequest(List.of("@", "#", "!")));
        assertThat(r.getSpecialCharacters()).containsExactly("@", "#", "!");
        assertThat(r.getSum()).isEqualTo("0");
        assertThat(r.getConcatString()).isEmpty();
    }

    @Test
    void lowercaseAlphabetsConvertedToUppercase() {
        BfhlResponse r = bfhlService.processData(
                new BfhlRequest(List.of("abc", "xyz")));
        assertThat(r.getAlphabets()).containsExactly("ABC", "XYZ");
    }

    @Test
    void zeroIsEven() {
        BfhlResponse r = bfhlService.processData(new BfhlRequest(List.of("0")));
        assertThat(r.getEvenNumbers()).containsExactly("0");
        assertThat(r.getOddNumbers()).isEmpty();
        assertThat(r.getSum()).isEqualTo("0");
    }

    @Test
    void userIdIsLowercaseWithDob() {
        BfhlResponse r = bfhlService.processData(new BfhlRequest(List.of("1")));
        // must match pattern  name_ddmmyyyy  all lowercase
        assertThat(r.getUserId()).matches("[a-z][a-z_]+_\\d{8}");
    }

    // ── Integration Tests (controller layer via MockMvc) ──────────────────────

    @Test
    void postEndpointReturns200_andCorrectBody() throws Exception {
        mockMvc.perform(post("/bfhl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"data":["a","1","334","4","R","$"]}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.is_success").value(true))
                .andExpect(jsonPath("$.odd_numbers[0]").value("1"))
                .andExpect(jsonPath("$.even_numbers[0]").value("334"))
                .andExpect(jsonPath("$.alphabets[0]").value("A"))
                .andExpect(jsonPath("$.sum").value("339"))
                .andExpect(jsonPath("$.concat_string").value("Ra"));
    }

    @Test
    void missingDataFieldReturns400() throws Exception {
        mockMvc.perform(post("/bfhl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.is_success").value(false));
    }

    @Test
    void malformedJsonReturns400() throws Exception {
        mockMvc.perform(post("/bfhl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("not-json"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.is_success").value(false));
    }
}
