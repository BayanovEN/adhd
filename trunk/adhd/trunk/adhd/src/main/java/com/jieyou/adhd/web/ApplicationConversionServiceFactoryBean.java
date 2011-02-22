package com.jieyou.adhd.web;

import com.jieyou.adhd.domain.Answer;
import com.jieyou.adhd.domain.Question;
import com.jieyou.adhd.domain.Record;
import com.jieyou.adhd.domain.Scale;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.roo.addon.web.mvc.controller.RooConversionService;
        
/**
 * A central place to register application Converters and Formatters. 
 */
@RooConversionService
public class ApplicationConversionServiceFactoryBean extends FormattingConversionServiceFactoryBean {

	@Override
	protected void installFormatters(FormatterRegistry registry) {
		super.installFormatters(registry);
		// Register application converters and formatters
	}
	

	Converter<Question, String> getQuestionConverter() {
        return new Converter<Question, String>() {
            public String convert(Question source) {
                return new StringBuilder().append(source.getSectionDescription()).append(" ").append(source.getQuestionNo()).append(" ").append(source.getQuestionContent()).toString();
            }
        };
    }

	Converter<Scale, String> getScaleConverter() {
        return new Converter<Scale, String>() {
            public String convert(Scale source) {
                return new StringBuilder().append(source.getScaleName()).append(" ").append(source.getDescription()).toString();
            }
        };
    }

	Converter<Record, String> getRecordConverter() {
        return new Converter<Record, String>() {
            public String convert(Record source) {
                return new StringBuilder().append(source.getPatientId()).append(" ").append(source.getAnswers()).append(" ").append(source.getDoneDay()).toString();
            }
        };
    }

	Converter<Answer, String> getAnswerConverter() {
        return new Converter<Answer, String>() {
            public String convert(Answer source) {
                return new StringBuilder().append(source.getAnswerDescription()).append(" ").append(source.getScore()).toString();
            }
        };
    }

	public void installLabelConverters(FormatterRegistry registry) {
        registry.addConverter(getQuestionConverter());
        registry.addConverter(getScaleConverter());
        registry.addConverter(getRecordConverter());
        registry.addConverter(getAnswerConverter());
    }

	public void afterPropertiesSet() {
        super.afterPropertiesSet();
        installLabelConverters(getObject());
    }
}
