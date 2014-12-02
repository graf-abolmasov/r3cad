<%@ page import="com.r3.dataset.pillar.PillarType; com.r3.dataset.pillar.WireSuspensionType; com.r3.dataset.pillar.IronCrampAnchorType; com.r3.dataset.CurveType; com.r3.dataset.InsulationJointUsingApATek; com.r3.dataset.turnout.ControlType; com.r3.dataset.SleepersType; com.r3.dataset.turnout.RailsType; com.r3.dataset.turnout.Model; com.r3.dataset.turnout.Orientation; com.r3.dataset.turnout.Direct; com.r3.dataset.turnout.TurnoutType" %>
define(function() {
  var i18n = jQuery.i18n;
  i18n.t = jQuery.i18n.prop;

  jQuery.i18n.properties({
    name: "messages",
    path: "${g.createLink(controller: 'I18N')}/",
    mode: "map",
    language: "${lang}"
  });

  i18n.enums = {};

  <g:set var="turnoutEnumClasses" value="[Model, Direct, RailsType, TurnoutType, Orientation, SleepersType, ControlType]"/>
  <g:each in="${turnoutEnumClasses}" var="enumClass">
  i18n.enums.${enumClass.simpleName} = ${i18n.selectOptionsJsArray(enumClass: enumClass)};
  </g:each>

  i18n.enums.InsulationJointUsingApATek = ${i18n.selectOptionsJsArray(enumClass: InsulationJointUsingApATek)};

  i18n.enums.CurveType = ${i18n.selectOptionsJsArray(enumClass: CurveType)};

  i18n.enums.PillarType = ${i18n.selectOptionsJsArray(enumClass: PillarType)};
  i18n.enums.IronCrampAnchorType = ${i18n.selectOptionsJsArray(enumClass: IronCrampAnchorType)};
  i18n.enums.WireSuspensionType = ${i18n.selectOptionsJsArray(enumClass: WireSuspensionType)};

  return i18n
});
