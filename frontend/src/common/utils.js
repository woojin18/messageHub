/**
 * @description This method is responsile for creating the object with mirror key and values
 * and also add prefix to values if available
 * @param {Array<string>} arr Array of strings
 * @param {string} prefix prefix
 * @returns {Object} object with mirror keys generated from the array of strings
 */
export const reflectKeys = (arr = [], prefix) =>
  arr.reduce((obj, key) => {
    const value = prefix ? prefix + ' ' + key : key;
    obj[key] = value;

    return obj;
  }, {});

export const form2obj = function(form) {
  var queryStr = serializeForm(form);
  return qstr2obj(queryStr);
};

/*!
 * Serialize all form data into a query string
 * (c) 2018 Chris Ferdinandi, MIT License, https://gomakethings.com
 * @param  {Node}   form The form to serialize
 * @return {String}      The serialized form data
 */
export const serializeForm = function(form) {
  // Setup our serialized data
  var serialized = [];

  // Loop through each field in the form
  for (var i = 0; i < form.elements.length; i++) {
    var field = form.elements[i];

    // Don't serialize fields without a name, submits, buttons, file and reset inputs, and disabled fields
    if (
      !field.name ||
      field.disabled ||
      field.type === 'file' ||
      field.type === 'reset' ||
      field.type === 'submit' ||
      field.type === 'button'
    )
      continue;

    // If a multi-select, get all selections
    if (field.type === 'select-multiple') {
      for (var n = 0; n < field.options.length; n++) {
        if (!field.options[n].selected) continue;
        serialized.push(encodeURIComponent(field.name) + '=' + encodeURIComponent(field.options[n].value));
      }
    }

    // Convert field data to a query string
    else if ((field.type !== 'checkbox' && field.type !== 'radio') || field.checked) {
      serialized.push(encodeURIComponent(field.name) + '=' + encodeURIComponent(field.value));
    }
  }

  return serialized.join('&');
};

export const qstr2obj = function(qstr) {
  let obj = {};
  if (qstr) {
    qstr.split('&').map(item => {
      const [k, v] = item.split('=');
      v ? (obj[k] = v) : null;
    });
  }
  return obj;
};

export const getCookie = function(name) {
  var value = '; ' + document.cookie;
  var parts = value.split('; ' + name + '=');
  if (parts.length == 2)
    return parts
      .pop()
      .split(';')
      .shift();
};

import { Base64 } from 'js-base64';

export const base64encode = function(str) {
  return Base64.encode(str);
};

export const base64decode = function(b64) {
  return Base64.decode(b64);
};

export const getGwSuccessCode = function() {
  return '10000';
};

export const isGwSuccess = function(code) {
  return getGwSuccessCode() == code;
};

let loadOverlap = [];
export const loadingLayer = (type) => {
  if (type) {
    loadOverlap.push('add');
  } else {
    loadOverlap.pop();
  }

  if (loadOverlap.length > 0) {
    document.querySelector('html > body').style.overflow = 'hidden'; // 스크롤 block
    document.getElementsByClassName('loading_layer')[0].style.display = 'block';
  } else {
    document.querySelector('html > body').style.removeProperty('overflow'); // 스크롤 allow
    document.getElementsByClassName('loading_layer')[0].style.display = 'none';
  }
};
