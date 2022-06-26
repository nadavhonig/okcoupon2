package com.okcoupon.okcoupon.advise;

import com.okcoupon.okcoupon.utils.ConsoleColors;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
public class ErrorDetail {
    private String error;
    private String description;

    /**
     * get method
     * @return string error
     */
    public String getError() {
        return error;
    }

    /**
     *  set the error
     * @param error string indicates of the error
     */

    public void setError(String error) {
        this.error = error;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ErrorDetail that = (ErrorDetail) o;
        return Objects.equals(error, that.error) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(error, description);
    }

    @Override
    public String toString() {
        return ConsoleColors.RED_BOLD + "ErrorDetail{" +
                "error='" + error + '\'' +
                ", description='" + description + '\'' +
                '}' + ConsoleColors.RESET;
    }
}
